package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The short variant of patients information
 * 
 * <p>
 * Clase Java para PatientInfo complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PatientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClinicianID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientInfo", propOrder = { "id", "surname", "name", "clinicianid" })
public class PatientInfo
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "Surname", required = true)
	protected String surname;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "ClinicianID", required = true)
	protected String clinicianid;


	/**
	 * Obtiene el valor de la propiedad id.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getID()
	{
		return id;
	}


	/**
	 * Define el valor de la propiedad id.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setID(String value)
	{
		this.id = value;
	}
	
	
	/**
	 * Obtiene el valor de la propiedad ClinicianID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClinicianID()
	{
		return clinicianid;
	}


	/**
	 * Define el valor de la propiedad ClinicianID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setClinicianID(String value)
	{
		this.clinicianid = value;
	}


	/**
	 * Obtiene el valor de la propiedad surname.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSurname()
	{
		return surname;
	}


	/**
	 * Define el valor de la propiedad surname.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSurname(String value)
	{
		this.surname = value;
	}


	/**
	 * Obtiene el valor de la propiedad name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Define el valor de la propiedad name.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value)
	{
		this.name = value;
	}
	

}
