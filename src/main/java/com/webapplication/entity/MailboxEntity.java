package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the mailbox database table.
 * 
 */
@Entity
@Table(name="Mailbox")
public class MailboxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAILBOX_ID")
	private Integer mailboxId;

	@OneToOne
	@JsonIgnore
	private UserEntity user;

	@OneToMany(mappedBy = "inbox",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Column(name = "inbox")
	private List<MessageEntity> inbox = new ArrayList<>();

	@OneToMany(mappedBy = "outbox",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Column(name = "outbox")
	private List<MessageEntity> outbox = new ArrayList<>();

	public MailboxEntity() {
	}

	public Integer getMailboxId() {
		return mailboxId;
	}

	public void setMailboxId(Integer mailboxId) {
		this.mailboxId = mailboxId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<MessageEntity> getInbox() {
		return inbox;
	}

	public void setInbox(List<MessageEntity> inbox) {
		this.inbox = inbox;
	}

	public List<MessageEntity> getOutbox() {
		return outbox;
	}

	public void setOutbox(List<MessageEntity> outbox) {
		this.outbox = outbox;
	}


}