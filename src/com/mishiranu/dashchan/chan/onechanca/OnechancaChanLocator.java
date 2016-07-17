package com.mishiranu.dashchan.chan.onechanca;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;

import chan.content.ChanLocator;
import chan.util.StringUtils;

public class OnechancaChanLocator extends ChanLocator
{
	private static final Pattern BOARD_PATH = Pattern.compile("/(\\w+|news/all|news/cat/\\w+)(?:/(?:\\d+/?)?)?");
	private static final Pattern THREAD_PATH = Pattern.compile("/\\w+/res/(\\d+)/?");
	private static final Pattern ATTACHMENT_PATH = Pattern.compile("/uploads/(\\w+)/\\d+\\.\\w+");
	
	public OnechancaChanLocator()
	{
		addChanHost("1chan.ca");
		addConvertableChanHost("www.1chan.ca");
		setHttpsMode(HttpsMode.CONFIGURABLE);
	}
	
	@Override
	public boolean isBoardUri(Uri uri)
	{
		return isChanHostOrRelative(uri) && isPathMatches(uri, BOARD_PATH);
	}
	
	@Override
	public boolean isThreadUri(Uri uri)
	{
		return isChanHostOrRelative(uri) && isPathMatches(uri, THREAD_PATH);
	}
	
	@Override
	public boolean isAttachmentUri(Uri uri)
	{
		return isChanHostOrRelative(uri) && isPathMatches(uri, ATTACHMENT_PATH);
	}
	
	@Override
	public String getBoardName(Uri uri)
	{
		Matcher matcher = BOARD_PATH.matcher(StringUtils.emptyIfNull(uri.getPath()));
		if (matcher.matches())
		{
			String boardName = matcher.group(1);
			if ("news/all".equals(boardName)) return "news-all";
			if (boardName.contains("/cat/")) return boardName.replace("/cat/", "-");
			return boardName;
		}
		else
		{
			List<String> segments = uri.getPathSegments();
			if (segments.size() > 0) return segments.get(0);
			return null;
		}
	}
	
	@Override
	public String getThreadNumber(Uri uri)
	{
		return getGroupValue(uri.getPath(), THREAD_PATH, 1);
	}
	
	@Override
	public String getPostNumber(Uri uri)
	{
		return uri.getFragment();
	}
	
	private String transformBoardNameForBuild(String boardName)
	{
		if ("news-all".equals(boardName)) boardName = "news/all";
		else if (boardName != null) boardName = boardName.replace("-", "/cat/");
		return boardName;
	}
	
	@Override
	public Uri createBoardUri(String boardName, int pageNumber)
	{
		boardName = transformBoardNameForBuild(boardName);
		return pageNumber > 0 ? buildPath(boardName, Integer.toString(pageNumber), "") : buildPath(boardName, "");
	}
	
	@Override
	public Uri createThreadUri(String boardName, String threadNumber)
	{
		boardName = transformBoardNameForBuild(boardName);
		return buildPath(boardName, "res", threadNumber, "");
	}
	
	@Override
	public Uri createPostUri(String boardName, String threadNumber, String postNumber)
	{
		return createThreadUri(boardName, threadNumber).buildUpon().fragment(postNumber).build();
	}
}