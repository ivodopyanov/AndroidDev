/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.game.controller.events.RequestBallMoveHandler;
import ru.naumen.core.game.controller.events.RequestBoardRotateHandler;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public interface PlayerController extends RequestBoardRotateHandler, RequestBallMoveHandler
{
}