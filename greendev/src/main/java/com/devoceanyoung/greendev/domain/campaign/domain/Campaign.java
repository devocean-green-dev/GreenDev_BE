package com.devoceanyoung.greendev.domain.campaign.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.devoceanyoung.greendev.domain.campaign.dto.CampaignReqDto;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Campaign extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_id", updatable = false)
	private Long campaignId;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(length = 50)
	private String date;

	@Column(columnDefinition = "TEXT") // 길이 제한이 없다.
	private String description;

	@Column(length = 50)
	private String category;

	private String campaignimageUrl;

	private Integer joinCount;

	@OneToMany(mappedBy = "campaign")
	private List<Participation> participations = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;

	@OneToMany(mappedBy = "campaign")
	private List<Post> posts = new ArrayList<>();

	@Builder
	public Campaign(String title, String date, String description, String category,
		String campaignimageUrl, Member member) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.category = category;
		this.campaignimageUrl = campaignimageUrl;
		this.writer = member;
		this.joinCount = 0;
	}

	public void updateContent(final CampaignReqDto reqDto){
		this.title = reqDto.getTitle();
		this.description= reqDto.getDescription();
		this.date = reqDto.getDate();
		this.category = reqDto.getCategory();
		this.campaignimageUrl = reqDto.getImageUrl();
	}

	public void increaseJoinCount() {
		this.joinCount += 1;
	}

	public Long getJoinMemberCount() {
		return participations.stream()
			.map(Participation::getMember)
			.distinct()
			.count();
	}

	public Long getTotalJoinCount() {
		return participations.stream()
			.mapToLong(Participation::getJoinCount)
			.sum();
	}

	public void addPost(Post post) {
		posts.add(post);
		post.setCampaign(this);
	}
}

