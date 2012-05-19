package org.skycastle.server.services.network

import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import org.apache.mina.filter.codec.ProtocolCodecFilter
import java.net.InetSocketAddress
import org.apache.mina.core.service.IoHandlerAdapter
import org.skycastle.server.utils.Logging
import org.apache.mina.core.session.{IoSession, IdleStatus}
import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap}
import org.skycastle.server.registry.Registry
import org.apache.mina.filter.executor.{OrderedThreadPoolExecutor, ExecutorFilter}
import org.skycastle.server.services.network.protocol.binary.BinaryProtocol

/**
 *
 */
class MinaNetworkService(registry: Registry,
                         port: Int,
                         closeSessionOnIdle: Boolean = true,
                         bufferSize: Int = 2048,
                         idleTimeSeconds: Int = 30) extends IoHandlerAdapter with NetworkService with Logging {

  private var acceptor: NioSocketAcceptor = null
  private val sessions: ConcurrentMap[Long, GameSession] = new ConcurrentHashMap[Long, GameSession]()
  private var executor: OrderedThreadPoolExecutor = null

  def init() {
    if (acceptor != null) throw new Error("Network service already started!")

    acceptor = createAcceptor()
  }

  def shutdown() {
    acceptor.unbind()
    executor.shutdown()
  }

  private def createAcceptor(): NioSocketAcceptor = {
    val acceptor = new NioSocketAcceptor()

    executor = new OrderedThreadPoolExecutor()

    // TODO: Add possibility of blacklisting certain IP numbers / ranges for counter-DDoS and other purposes
    // (Although IP blocks for counter-DDoS purposes should be at a lower router level ideally)

    // Multi-threaded executor pool
    acceptor.getFilterChain.addLast("executor", new ExecutorFilter(executor))

    // TODO: Add encryption filter

    // Decode traffic
    acceptor.getFilterChain.addLast("codec", new ProtocolCodecFilter(new BinaryProtocol()))

    // Ensure authentication before message passes this
    acceptor.getFilterChain.addLast("authentication", new AuthenticationFilter(registry))


    // Set handler of connections
    acceptor.setHandler(this)

    // Set buffer size
    acceptor.getSessionConfig.setReadBufferSize(bufferSize)

    // Set time after which idle is called
    acceptor.getSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, idleTimeSeconds)

    // Listen to specified port
    acceptor.bind(new InetSocketAddress(port))

    acceptor
  }


  override def sessionOpened(session: IoSession) {
    // Create player session object
    val gameSession = new GameSession(registry, session)
    sessions.put(session.getId, gameSession)
  }

  override def messageReceived(session: IoSession, message: Any) {
    val gameSession: GameSession = sessions.get(session.getId)
    if (gameSession == null) throw new Exception("Message received for unknown session " + session.getId)

    gameSession.handleMessage(message)
  }

  override def sessionClosed(session: IoSession) {
    val gameSession: GameSession = sessions.get(session.getId)
    if (gameSession == null) throw new Exception("Session closed for unknown session " + session.getId)

    gameSession.handleSessionClosed()
    sessions.remove(session.getId)
  }

  override def sessionIdle(session: IoSession, status: IdleStatus) {
    if (closeSessionOnIdle && (status == IdleStatus.READER_IDLE || status == IdleStatus.BOTH_IDLE)) {
      session.close(false)
    }
  }

  override def exceptionCaught(session: IoSession, cause: Throwable) {
    log.error("Exception while handling message for session " + session.getId + ": " + cause.getMessage, cause)
  }

}
