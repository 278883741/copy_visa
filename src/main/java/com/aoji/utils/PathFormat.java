package com.aoji.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathFormat {
	
	private static final String TIME = "time";
	private static final String FULL_YEAR = "yyyy";
	private static final String YEAR = "yy";
	private static final String MONTH = "mm";
	private static final String DAY = "dd";
	private static final String HOUR = "hh";
	private static final String MINUTE = "ii";
	private static final String SECOND = "ss";
	private static final String RAND = "rand";
	private static final String BUSPATH = "buspath";
	private static final String USER = "user";
	
	private static Date currentDate = null;
	
	public static String parse ( String input ) {
		
		Pattern pattern = Pattern.compile( "\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE  );
		Matcher matcher = pattern.matcher(input);

		com.aoji.utils.PathFormat.currentDate = new Date();
		
		StringBuffer sb = new StringBuffer();
		
		while ( matcher.find() ) {
			
			matcher.appendReplacement(sb, com.aoji.utils.PathFormat.getString( matcher.group( 1 ) , null,null) );
			
		}
		
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	
	/**
	 * 格式化路径, 把windows路径替换成标准路径
	 * @param input 待格式化的路径
	 * @return 格式化后的路径
	 */
	public static String format ( String input ) {
		
		return input.replace( "\\", "/" );
		
	}

	private static Pattern pattern = Pattern.compile( "\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE  );
	public static String parse ( String input, String filename ,String buspath,String user) {

		Matcher matcher = pattern.matcher(input);
		String matchStr = null;

		com.aoji.utils.PathFormat.currentDate = new Date();
		
		StringBuffer sb = new StringBuffer();
		
		while ( matcher.find() ) {
			
			matchStr = matcher.group( 1 );
			if ( matchStr.indexOf( "filename" ) != -1 ) {
				filename = filename.replace( "$", "\\$" ).replaceAll( "[\\/:*?\"<>|]", "" );
				matcher.appendReplacement(sb, filename );
			} else {
				matcher.appendReplacement(sb, com.aoji.utils.PathFormat.getString( matchStr ,buspath,user) );
			}
			
		}
		
		matcher.appendTail(sb);
		
		return sb.toString();
	}
		
	private static String getString ( String pattern ,String buspath,String user) {
		
		pattern = pattern.toLowerCase();
		
		// time 处理
		if ( pattern.indexOf( com.aoji.utils.PathFormat.TIME ) != -1 ) {
			return com.aoji.utils.PathFormat.getTimestamp();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.FULL_YEAR ) != -1 ) {
			return com.aoji.utils.PathFormat.getFullYear();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.YEAR ) != -1 ) {
			return com.aoji.utils.PathFormat.getYear();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.MONTH ) != -1 ) {
			return com.aoji.utils.PathFormat.getMonth();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.DAY ) != -1 ) {
			return com.aoji.utils.PathFormat.getDay();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.HOUR ) != -1 ) {
			return com.aoji.utils.PathFormat.getHour();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.MINUTE ) != -1 ) {
			return com.aoji.utils.PathFormat.getMinute();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.SECOND ) != -1 ) {
			return com.aoji.utils.PathFormat.getSecond();
		} else if ( pattern.indexOf( com.aoji.utils.PathFormat.RAND ) != -1 ) {
			return com.aoji.utils.PathFormat.getRandom( pattern );
		}else if(pattern.indexOf( com.aoji.utils.PathFormat.BUSPATH ) != -1 ){
			if(!StringUtils.hasText(buspath))
			{
				return  "";
			}

			return  buspath.trim();
		}else if(pattern.indexOf( com.aoji.utils.PathFormat.USER ) != -1 ){
			if(!StringUtils.hasText(user))
			{
				return  "";
			}
			return  user.toLowerCase()+"/";
		}
		return pattern;
	}

	private static String getTimestamp () {
		return System.currentTimeMillis() + "";
	}
	
	private static String getFullYear () {
		return new SimpleDateFormat( "yyyy" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getYear () {
		return new SimpleDateFormat( "yy" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getMonth () {
		return new SimpleDateFormat( "MM" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getDay () {
		return new SimpleDateFormat( "dd" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getHour () {
		return new SimpleDateFormat( "HH" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getMinute () {
		return new SimpleDateFormat( "mm" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getSecond () {
		return new SimpleDateFormat( "ss" ).format( com.aoji.utils.PathFormat.currentDate );
	}
	
	private static String getRandom ( String pattern ) {
		
		int length = 0;
		pattern = pattern.split( ":" )[ 1 ].trim();
		
		length = Integer.parseInt( pattern );
		
		return ( Math.random() + "" ).replace( ".", "" ).substring( 0, length );
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
