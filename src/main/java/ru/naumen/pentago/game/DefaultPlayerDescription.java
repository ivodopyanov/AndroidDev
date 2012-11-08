/**
 * 
 */
package ru.naumen.pentago.game;

import ru.naumen.pentago.game.model.Player.PlayerType;

/**
 * @author ivodopyanov
 * @since 08.11.2012
 * 
 */
public class DefaultPlayerDescription
{
    private final int titleCode;
    private final int ballResource;
    private final String code;
    private final PlayerType type;

    public DefaultPlayerDescription(int titleCode, String code, int ballResource, PlayerType type)
    {
        this.titleCode = titleCode;
        this.ballResource = ballResource;
        this.code = code;
        this.type = type;
    }

    public int getBallResource()
    {
        return ballResource;
    }

    public String getCode()
    {
        return code;
    }

    public int getTitleCode()
    {
        return titleCode;
    }

    public PlayerType getType()
    {
        return type;
    }

}
