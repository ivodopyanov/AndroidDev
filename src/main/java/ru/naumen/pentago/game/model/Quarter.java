/**
 * 
 */
package ru.naumen.pentago.game.model;

/**
 * @author ivodopyanov
 * @since 06.10.2012
 * 
 */
public class Quarter
{
    private static final Quarter[] QUARTER_POOL = new Quarter[] { new Quarter(true, true), new Quarter(false, true),
            new Quarter(true, false), new Quarter(false, false) };

    public static Quarter create(boolean left, boolean top)
    {
        return QUARTER_POOL[(left ? 0 : 1) + (top ? 0 : 2)];
    }

    public static Quarter create(int pos)
    {
        return QUARTER_POOL[pos];
    }

    private final boolean left;
    private final boolean top;

    private Quarter(boolean left, boolean top)
    {
        this.left = left;
        this.top = top;
    }

    public boolean isLeft()
    {
        return left;
    }

    public boolean isTop()
    {
        return top;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append(left ? "left" : "right").append("-").append(top ? "top" : "bottom")
                .toString();
    }
}
