/**
 * 
 */
package ru.naumen.pentago.player.controller.ai;

import android.os.Message;

/**
 * @author ivodopyanov
 * @since 05.10.2012
 * 
 */
public interface AICalculator
{
    Message obtainMessage();

    boolean sendMessage(Message message);
}