/**
 * 
 */
package ru.naumen.pentago.player.controller;

import ru.naumen.pentago.game.controller.events.RequestBallMoveHandler;
import ru.naumen.pentago.game.controller.events.RequestBoardRotateHandler;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public interface PlayerController extends RequestBoardRotateHandler, RequestBallMoveHandler
{
}