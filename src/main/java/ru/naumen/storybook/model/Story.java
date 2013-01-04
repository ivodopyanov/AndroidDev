package ru.naumen.storybook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class Story implements Parcelable
{
    private final String text;
    private final String title;

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>()
    {
        @Override
        public Story createFromParcel(Parcel in)
        {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size)
        {
            return new Story[size];
        }
    };

    public Story(Parcel in)
    {
        title = in.readString();
        text = in.readString();
    }

    public Story(String text, String title)
    {
        this.text = text;
        this.title = title;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public String getText()
    {
        return text;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(text);
    }
}