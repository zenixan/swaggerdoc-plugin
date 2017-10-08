package org.eu.fuzzy.swagger.model

/**
 * Represents a transfer protocol.
 */
sealed trait Protocol

object Protocol {

  /**
   * @see [[https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol Hypertext Transfer Protocol]]
   */
  case object http extends Protocol

  /**
   * @see [[https://en.wikipedia.org/wiki/HTTPS Secure Hypertext Transfer Protocol]]
   */
  case object https extends Protocol

  /**
   * @see [[https://en.wikipedia.org/wiki/WebSocket WebSocket]]
   */
  case object ws extends Protocol

  /**
   * @see [[https://en.wikipedia.org/wiki/WebSocket Encrypted WebSocket]]
   */
  case object wss extends Protocol

}