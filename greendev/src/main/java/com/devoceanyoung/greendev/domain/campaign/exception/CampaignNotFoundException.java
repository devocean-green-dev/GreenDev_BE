package com.devoceanyoung.greendev.domain.campaign.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class CampaignNotFoundException extends RuntimeException{

	public CampaignNotFoundException() {
		super(CAMPAIGN_NOT_FOUND);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
