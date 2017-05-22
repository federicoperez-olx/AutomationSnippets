package Utilities;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtilities 
{
	
	public static String ApplyRegex(String base, String regex)
	{
		return ApplyRegex(base, regex, 1);
	}
	
	public static String ApplyRegex(String base, String regex, int captureGroup)
	{
		if ( base.matches(regex) )
		{
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher(base);

			return mat.group(captureGroup);
			//base.replaceFirst(pat, "$1");
		}
		
		return null;
	}
	
}
