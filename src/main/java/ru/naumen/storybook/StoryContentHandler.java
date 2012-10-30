/**
 * 
 */
package ru.naumen.storybook;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ru.naumen.storybook.model.Story;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class StoryContentHandler extends DefaultHandler
{

    private final List<Story> stories;

    public StoryContentHandler(List<Story> stories)
    {
        this.stories = stories;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {

        if (localName.equals("story"))
        {
            String title = attributes.getValue("title");
            String text = attributes.getValue("text");
            Story story = new Story(text, title);
            stories.add(story);
        }
    }
}