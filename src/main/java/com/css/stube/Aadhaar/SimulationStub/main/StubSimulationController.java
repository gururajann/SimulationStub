package com.css.stube.Aadhaar.SimulationStub.main;


import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//@RequestMapping("/aadhaar")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StubSimulationController {
	
	/*
	 * @RequestMapping(value="/getAadhaarReferenceNo",method=RequestMethod.GET)
	 * public String getAadhaarReferenceNo(@RequestParam("aadhaarno") String
	 * aadhaarno) {
	 * 
	 * return LoadData.aadhaarcache.get(aadhaarno); }
	 */
	
	@RequestMapping(value = "/storeAadhaarData_01", method = RequestMethod.POST)
	public String getAadhaarReferenceNo(@RequestBody String reqData) {
		JSONObject mainDataObject = null;
		JSONObject hdrContent = new JSONObject();
		JSONObject bdyContent = new JSONObject();
		JSONObject resData = new JSONObject();
		
		String aadharNo = null;
		String responseStr = null;
		try {
			mainDataObject =  new JSONObject(reqData);
			aadharNo = (String) ((JSONObject) ((JSONObject) mainDataObject
					.get("storeAadhaarDataReq")).get("msgBdy")).get("uid");
			responseStr = LoadData.aadhaarcache.get(aadharNo.trim());
			hdrContent.put("rslt", "OK");
			bdyContent.put("reference", responseStr);			
			resData.put("msgHdr", hdrContent);
			resData.put("msgBdy", bdyContent);
			
			return  resData.toString();
		} catch (Exception e) {
			System.out.println("In Error ....");
			System.out.println(e.getMessage());
			return null;
		}

	}
	
	@RequestMapping(value = "/RetrieveAadhaarData_01", method = RequestMethod.POST)
	public String getAadhaarNo(@RequestBody String reqData) {
		JSONObject mainDataObject = null;
		JSONObject hdrContent = new JSONObject();
		JSONObject bdyContent = new JSONObject();
		JSONObject resData = new JSONObject();
		
		String referenceNo = null;
		String responseStr = null;
		try {
			mainDataObject =  new JSONObject(reqData);
			referenceNo = (String) ((JSONObject) ((JSONObject) mainDataObject
					.get("retrieveAadhaarDataReq")).get("msgBdy")).get("reference");
			responseStr = LoadData.referencecache.get(referenceNo.trim());
			hdrContent.put("rslt", "OK");
			bdyContent.put("reference", referenceNo);
			bdyContent.put("uid",responseStr);
			bdyContent.put("uid-token",JSONObject.NULL);
			resData.put("msgHdr", hdrContent);
			resData.put("msgBdy", bdyContent);
			
			return  resData.toString();
		} catch (Exception e) {
			System.out.println("In Error ....");
			System.out.println(e.getMessage());
			return null;
		}

	}

	
	@RequestMapping(value="/crebreu/crif/highmark/v2", method=RequestMethod.POST)
	public String loadXmlData(@RequestBody String xmlData) {
		String xmlString = null;
//		String responseData = null;
		try {
			String reqCount = new SimpleDateFormat("MMddmmssSSS").format(new Date());
			
			/*
			 * responseData =
			 * "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
			 * "<REQUEST-RESPONSE>\r\n" + "    <STATUS>SUCCESS</STATUS>\r\n" +
			 * "    <REQUEST-ID>" + reqCount + "</REQUEST-ID>\r\n" + "</REQUEST-RESPONSE>";
			 */
			
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
			Element rootElem = document.createElement("REQUEST-RESPONSE");
			document.appendChild(rootElem);			
			Element statusElem = document.createElement("STATUS");
			statusElem.appendChild(document.createTextNode("SUCCESS"));
			rootElem.appendChild(statusElem);			
			Element reqIdElem = document.createElement("REQUEST-ID");
			reqIdElem.appendChild(document.createTextNode(reqCount));
			rootElem.appendChild(reqIdElem);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);			
			StringWriter sWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(sWriter);
			transformer.transform(domSource, streamResult);
			xmlString = sWriter.getBuffer().toString();
			
//			return responseData;
			return xmlString;			
		} catch (Exception e) {
			System.out.println("In Error ....");
			System.out.println(e.getMessage());
			return xmlString;
		}
		
	}
	 
	 @RequestMapping(value="reload",method=RequestMethod.GET)
	    public  String reLoad(@RequestParam(required = false) String filename) throws Exception{
		 
		 if(filename==null)		 
		 return  LoadData.load(SimulationStubApplication.filename);
		 else
			 return  LoadData.load(filename);
	    }
	 

	 @RequestMapping(value="numOfAadhaarLoaded",method=RequestMethod.GET)
	    public  String numOfAadhaarLoaded() {
		 
		 return  ""+ LoadData.aadhaarcache.size();
	    }
	 
	 
}


