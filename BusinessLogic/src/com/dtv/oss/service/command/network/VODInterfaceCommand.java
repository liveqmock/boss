package com.dtv.oss.service.command.network;

import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.component.VODInterfaceService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.ModifyVODInterfaceEJBEvent;

public class VODInterfaceCommand extends Command {
	private VODInterfaceService service = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		if (ev == null || !(ev instanceof ModifyVODInterfaceEJBEvent)) {
			return null;
		}
		String remoteHostAddress = ev.getRemoteHostAddress();
		service = new VODInterfaceService(remoteHostAddress, ev.getOperatorID());
		ModifyVODInterfaceEJBEvent event = (ModifyVODInterfaceEJBEvent) ev;
		int actionType = event.getActionType();		
		switch (actionType) {

		case (ModifyVODInterfaceEJBEvent.HOST_NEW): {
			createVODInterfaceHost((VODInterfaceHostDTO) event.getDto());
			break;
		}

		case (ModifyVODInterfaceEJBEvent.HOST_UPDATE): {
			updateVODInterfaceHost((VODInterfaceHostDTO) event.getDto());
			break;
		}
		case (ModifyVODInterfaceEJBEvent.PRODUCT_NEW): {
			createVODInterfaceProduct((VODInterfaceProductDTO) event.getDto());
			break;
		}
		case (ModifyVODInterfaceEJBEvent.PRODUCT_UPDATE): {
			updateVODInterfaceProduct((VODInterfaceProductDTO) event.getDto());
			break;
		}

		}
		
		return null;
	}

	private void createVODInterfaceHost(VODInterfaceHostDTO dto)
			throws CommandException {
		try {
			service.createVODHostObject(dto);
		} catch (ServiceException e) {

			throw new CommandException(e.getMessage());
		}
	}

	private void updateVODInterfaceHost(VODInterfaceHostDTO dto)
			throws CommandException {
		try {
			service.updateVODHostObject(dto);
		} catch (ServiceException e) {

			throw new CommandException(e.getMessage());
		}
	}

	private void createVODInterfaceProduct(VODInterfaceProductDTO dto)
			throws CommandException {
		try {
			service.createVODProductObject(dto);
		} catch (ServiceException e) {

			throw new CommandException(e.getMessage());
		}
	}

	private void updateVODInterfaceProduct(VODInterfaceProductDTO dto)
			throws CommandException {
		try {
			service.updateVODProductObject(dto);
		} catch (ServiceException e) {

			throw new CommandException(e.getMessage());
		}
	}

}
