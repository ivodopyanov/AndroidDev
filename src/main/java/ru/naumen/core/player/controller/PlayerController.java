/**
 * 
 */
package ru.naumen.core.player.controller;

import ru.naumen.core.game.controller.events.RequestBallMoveEvent;
import ru.naumen.core.game.controller.events.RequestBoardRotateEvent;

import com.google.common.eventbus.Subscribe;

/**
 * @author ivodopyanov
 * @since 21.09.2012
 * 
 */
public interface PlayerController
{
    @Subscribe
    void requestBoardRotation(RequestBoardRotateEvent event);

    @Subscribe
    void requestMove(RequestBallMoveEvent event);
}