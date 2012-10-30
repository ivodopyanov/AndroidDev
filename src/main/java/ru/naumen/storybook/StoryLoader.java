/**
 * 
 */
package ru.naumen.storybook;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import ru.naumen.storybook.model.Story;
import android.content.Context;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class StoryLoader
{
    private final Context context;

    public StoryLoader(Context context)
    {
        this.context = context;
    }

    public List<Story> load(String file) throws IOException, ParserConfigurationException, SAXException
    {
        List<Story> result = new LinkedList<Story>();
        InputStream is = context.getAssets().open(file);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xmlReader = sp.getXMLReader();
        xmlReader.setContentHandler(new StoryContentHandler(result));
        xmlReader.parse(new InputSource(is));
        return result;
    }
}