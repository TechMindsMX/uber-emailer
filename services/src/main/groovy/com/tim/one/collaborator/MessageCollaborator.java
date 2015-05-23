package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.TransactionType;
import com.tim.one.bean.mail.CierreBean;
import com.tim.one.helper.MessagePackerHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.User;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.UserRepository;

@Component
public class MessageCollaborator {

	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private UserCollaborator userCollaborator;
	@Autowired
	private MessagePackerHelper messagePackerHelper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
  private ProjectFinancialDataRepository projectFinancialDataRepository;

	public void sendAutorizadoStatusNotifications(Project project) {
		messagePacker.sendSimpleMessage(project.getName(), project.getUser().getId(), MessageType.CIERRE_PUBLICACION_PRODUCTOR);
		Set<Integer> financiers = userCollaborator.getPartnersByType(project.getProjectFinancialData(), TransactionType.FUNDING);
		Set<Integer> investors = userCollaborator.getPartnersByType(project.getProjectFinancialData(), TransactionType.INVESTMENT);
		financiers.addAll(investors);
		for (Integer userId : financiers) {
			messagePacker.sendSimpleMessage(project.getName(), userId, MessageType.CIERRE_PUBLICACION_SOCIO);
		}
	}

	public void sendBreakevenReachedPartners(Project project) {
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		Set<Integer> financiers = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.FUNDING);
		Set<Integer> investors = userCollaborator.getPartnersByType(projectFinancialData, TransactionType.INVESTMENT);
		financiers.addAll(investors);
		for (Integer userId : financiers) {
			CierreBean bean = messagePackerHelper.createCierreBean();
			bean.setProjectName(project.getName());
			BigDecimal difference = projectFinancialData.getBudget().subtract(projectFinancialData.getBalance());
			bean.setDifference(difference.toString());
			User partner = userRepository.findOne(userId);
			bean.setEmail(partner.getEmail());
			bean.setType(MessageType.BE_SOCIO);
		}
	}
	
}
