package com.dhanya.mini.websitebuilder.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dhanya.mini.websitebuilder.utils.FileUploadUtils;
import com.dhanya.mini.websitebuilder.utils.UploadStatus;
import com.leela.mini.weeblycommonlib.dao.PageDao;
import com.leela.mini.weeblycommonlib.domain.ElementDomain;
import com.leela.mini.weeblycommonlib.domain.PageDomain;

/**
 * Controller for handling Weebly's customer's assets.
 *  
 * @author Leela
 */
@Controller
@RequestMapping("/element")
public class AssetController {
	
	private static final Logger log = Logger.getLogger(AssetController.class.getName());

	private PageDao pageDao;
	
	@Autowired
	public AssetController(PageDao pageDao) {
		this.pageDao = pageDao;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadForm() {
		return "uploadElement";
	}
	
	@RequestMapping(value = "/upload/{uuid}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ModelAndView upload(@PathVariable String uuid,@RequestParam("uploadFile") MultipartFile uploadFile, Model model) {	
		if(uploadFile != null) {	
			ElementDomain elementDomain = new ElementDomain();
			String elementUrl = "src/main/webapp/WEB-INF/static/"+uploadFile.getOriginalFilename();
			InputStream inputStream = null;
		    OutputStream outputStream =null;
			
		    if(!FileUploadUtils.isAllowedFileType(uploadFile.getContentType())) {
		    	model.addAttribute("UploadStatus", new UploadStatus("Faild:Image type not supported","n/a"));
		    	return  new ModelAndView("uploaded");
		    }
		
		 if(!FileUploadUtils.isAllowedFileSize(uploadFile.getSize())) {
				model.addAttribute("UploadStatus", new UploadStatus("Failed: Image size too big", "n/a"));
				return  new ModelAndView("uploaded");
			 }
		
			try {
	              inputStream = uploadFile.getInputStream();
	              outputStream = new FileOutputStream(elementUrl);        
	              int read = 0;
	      		  byte[] bytes = new byte[1024];
	                
	      		  while ((read = inputStream.read(bytes)) != -1) {      	 
	      			      outputStream.write(bytes, 0, read);      
	  		      }
	              outputStream.close();
	              String elementuuId = UUID.randomUUID().toString();
	              elementDomain.setElementUniqueId(elementuuId);
	              elementDomain.setContent(elementUrl);
	              elementDomain.setType("image"); 
	              
	              PageDomain pageDomain = new PageDomain();
	              pageDomain.setUuid(uuid);
	              pageDomain.setName(pageDao.getPage(uuid).getName());
	              pageDomain.addingSingleElementDomainList(elementDomain);
	              pageDao.update(uuid, pageDomain);
	          
			} catch (IOException ex) {
				log.error("Error during upload of asset", ex);
			}
			model.addAttribute("elementDomain", elementDomain);
			model.addAttribute("UploadStatus", new UploadStatus("upload complete", elementUrl));
		return  new ModelAndView("uploaded");	
	}	
	return null;
 }	
}