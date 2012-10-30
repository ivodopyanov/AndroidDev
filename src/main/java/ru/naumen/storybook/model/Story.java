package ru.naumen.storybook.model;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class Story
{
    private final String text;
    private final String title;

    public Story(String text, String title)
    {
        this.text = text;
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public String getTitle()
    {
        return title;
    }
}