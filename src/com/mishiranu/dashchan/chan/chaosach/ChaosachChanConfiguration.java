package com.mishiranu.dashchan.chan.chaosach;

import android.util.Pair;

import chan.content.ChanConfiguration;

public class ChaosachChanConfiguration extends ChanConfiguration {
	public ChaosachChanConfiguration() {
		request(OPTION_READ_THREAD_PARTIALLY);
		request(OPTION_READ_SINGLE_POST);
		request(OPTION_READ_POSTS_COUNT);
		setDefaultName("anonymous");
		setBumpLimit(500);
		addCaptchaType("monaba");
	}

	@Override
	public Board obtainBoardConfiguration(String boardName) {
		Board board = new Board();
		board.allowCatalog = true;
		board.allowPosting = true;
		board.allowDeleting = true;
		return board;
	}

	@Override
	public Captcha obtainCustomCaptchaConfiguration(String captchaType) {
		if ("monaba".equals(captchaType)) {
			Captcha captcha = new Captcha();
			captcha.title = "Monaba";
			captcha.input = Captcha.Input.LATIN;
			captcha.validity = Captcha.Validity.LONG_LIFETIME;
			return captcha;
		}
		return null;
	}

	@Override
	public Posting obtainPostingConfiguration(String boardName, boolean newThread) {
		Posting posting = new Posting();
		posting.allowName = true;
		posting.allowSubject = true;
		posting.optionSage = boardName == null || !newThread;
		posting.attachmentCount = 10;
		posting.attachmentMimeTypes.add("image/*");
		posting.attachmentMimeTypes.add("video/webm");
		posting.attachmentMimeTypes.add("application/zip");
		posting.attachmentMimeTypes.add("application/rar");
		posting.attachmentMimeTypes.add("application/pdf");
		posting.attachmentMimeTypes.add("image/vnd.djvu");
		posting.attachmentMimeTypes.add("audio/mp3");
		posting.attachmentMimeTypes.add("audio/flac");
		posting.attachmentMimeTypes.add("application/ogg");
		posting.attachmentMimeTypes.add("application/x-shockwave-flash");
		posting.attachmentMimeTypes.add("text/css");
		posting.attachmentRatings.add(new Pair<>("1", "SFW"));
		posting.attachmentRatings.add(new Pair<>("2", "R15"));
		posting.attachmentRatings.add(new Pair<>("3", "R18"));
		posting.attachmentRatings.add(new Pair<>("4", "R18G"));
		return posting;
	}

	@Override
	public Deleting obtainDeletingConfiguration(String boardName) {
		Deleting deleting = new Deleting();
		deleting.password = true;
		deleting.multiplePosts = true;
		deleting.optionFilesOnly = true;
		return deleting;
	}
}