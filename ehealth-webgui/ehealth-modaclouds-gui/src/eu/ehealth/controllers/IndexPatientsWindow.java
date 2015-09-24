package eu.ehealth.controllers;

import java.rmi.RemoteException;
import java.util.Collection;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.ExternalService;


/**
 * This class manages al events from the patients index page
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class IndexPatientsWindow extends Window
{


	private static final long serialVersionUID = -4585592732261850018L;
	private String patid = null;


	/**
	 * Invoke this method to remove a patients. It won't work unless you have
	 * set the patid attribute before
	 */
	public void deletePatient()
	{
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			proxy.deletePatient(this.patid, userid);
			
			this.patid = null;
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../patients/index_content.zul");
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			Messagebox.show("#TXT# Error : " + re.getMessage(), "#TXT# Delete Patient", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method allow the user to remove patients from the index page with a
	 * confirmation dialog
	 * 
	 * @param id Id of the patient which is going to be deleted
	 */
	public void deletePatientMsg(String id)
	{
		this.patid = id;
		ConfirmDeletePatient auxwin = new ConfirmDeletePatient(id);
		Button btn = new Button();
		String text = Labels.getLabel("patients.delete.sure");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				deletePatient();
			}
		});
		auxwin.addEventListener("onClose", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				patid = null;
			}
		});
		auxwin.appendChild(btn);
		this.appendChild(auxwin);
		try
		{
			auxwin.doModal();
		}
		catch (Exception ee)
		{
			SystemDictionary.logException(ee);
		}
	}


	@SuppressWarnings("serial")
	public class ConfirmDeletePatient extends Window
	{


		public ConfirmDeletePatient(String id)
		{
			Label message = new Label();
			String text = Labels.getLabel("patients.delete.confirm");
			message.setValue(text + " " + id + "?");
			this.appendChild(message);

			Separator sep = new Separator();
			sep.setHeight("10px");
			this.appendChild(sep);

			String text2 = Labels.getLabel("patients.delete");
			this.setTitle(text2);
			this.setBorder("normal");
			this.setClosable(true);
		}
		
		
	}
	
	
	
	public Window servicePopup;


	/**
	 * 
	 * @throws InterruptedException
	 */
	public void createService() throws InterruptedException
	{
		servicePopup = (Window) Executions.createComponents("../extsrv/form.zul", this, null);
		((Button) servicePopup.getFellow("savebutton")).setVisible(true);
		String title = Labels.getLabel("extsrv.new");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}
	
	
	/**
	 * 
	 * @param srvid
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void modifyService(String srvid) throws RemoteException, InterruptedException
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		ExternalService defserv = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		ExternalService[] services = proxy.getAllExternalServicesArr(userid);
		for (int i = 0; i < services.length; i++)
		{
			if (services[i].getID().equals(srvid))
			{
				defserv = services[i];
				break;
			}
		}

		servicePopup = (Window) Executions.createComponents("../patients/msgform.zul", this, null);
		
		int selectedindex = 1;
		if (defserv.getType().equals(SystemDictionary.EXT_SERV_EXERCISES)) {
			selectedindex = 0;
		}
		((Listbox) servicePopup.getFellow("content_type")).setSelectedIndex(selectedindex);
		
		((Button) servicePopup.getFellow("cancelbutton")).setVisible(true);
		((Textbox) servicePopup.getFellow("srvidfield")).setValue(srvid);
		((Textbox) servicePopup.getFellow("srvdescfield")).setValue(defserv.getDescription());
		((Textbox) servicePopup.getFellow("srvuricfield")).setValue(defserv.getAddress());
		String title = Labels.getLabel("extsrv.update");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}


	/**
	 * 
	 * @param srvid
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void viewService(String srvid) throws RemoteException, InterruptedException
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		ExternalService defserv = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		/*ExternalService[] services = proxy.getAllExternalServicesArr(userid);
		for (int i = 0; i < services.length; i++)
		{
			if (services[i].getID().equals(srvid))
			{
				defserv = services[i];
				break;
			}
		}*/
		servicePopup = (Window) Executions.createComponents("../extsrv/form.zul", this, null);
		
		int selectedindex = 1;
		/*if (defserv.getType().equals(SystemDictionary.EXT_SERV_EXERCISES)) {
			selectedindex = 0;
		}*/
		((Listbox) servicePopup.getFellow("content_type")).setSelectedIndex(selectedindex);
		((Listbox) servicePopup.getFellow("content_type")).setDisabled(true);
		
		((Button) servicePopup.getFellow("cancelbutton")).setVisible(false);
		((Button) servicePopup.getFellow("savebutton")).setVisible(false);
		((Textbox) servicePopup.getFellow("srvidfield")).setValue(srvid);
		((Textbox) servicePopup.getFellow("srvidfield")).setReadonly(true);
		//((Textbox) servicePopup.getFellow("srvdescfield")).setValue(defserv.getDescription());
		((Textbox) servicePopup.getFellow("srvdescfield")).setReadonly(true);
		//((Textbox) servicePopup.getFellow("srvuricfield")).setValue(defserv.getAddress());
		((Textbox) servicePopup.getFellow("srvuricfield")).setReadonly(true);
		String title = Labels.getLabel("common.view");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}
	

}
