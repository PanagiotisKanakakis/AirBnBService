package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



/**
 * The persistent class for the message database table.
 * 
 */
@Entity(name = "Message")
public class MessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MESSAGE_ID")
	private Integer messageID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	private int isRead;
	private String messageText;
	private String subject;

	@ManyToOne
	@JoinColumn(name = "INBOX")
	@JsonIgnore
	private MailboxEntity inbox;

	@ManyToOne
	@JoinColumn(name = "OUTBOX")
	@JsonIgnore
	private MailboxEntity outbox;

	@ManyToOne
	@JoinColumn(name="FROM_USER")
	private UserEntity fromUser;

	@ManyToOne
	@JoinColumn(name="TO_USER")
	private UserEntity toUser;

	public MessageEntity() {
	}

	public int getMessageID() {
		return this.messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getIsRead() {
		return this.isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getMessageText() {
		return this.messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public UserEntity getFromUser() {
		return fromUser;
	}

	public UserEntity getToUser() {
		return toUser;
	}

	public void setFromUser(UserEntity fromUser) {
		this.fromUser = fromUser;
	}

	public void setToUser(UserEntity toUser) {
		this.toUser = toUser;
	}

	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}

	public MailboxEntity getInbox() {
		return inbox;
	}

	public void setInbox(MailboxEntity inbox) {
		this.inbox = inbox;
	}

	public MailboxEntity getOutbox() {
		return outbox;
	}

	public void setOutbox(MailboxEntity outbox) {
		this.outbox = outbox;
	}
}