package com.dhanya.mini.websitebuilder.utils;
import java.util.HashMap;

import org.springframework.util.StringUtils;

/**
 * Utility class for Asset management.
 *
 * @author Leela
 */
public class FileUploadUtils {
	
		private static final long MAX_SIZE = 1000000;	//1,000,000 bytes (1mb)
		
		private static HashMap<String, Boolean> allowedFileTypes = null;
		
		public static boolean isAllowedFileSize(long fileSize) {
			return fileSize > MAX_SIZE ? false: true;
		}
		
		public static boolean isAllowedFileType(String fileType) {
			if(StringUtils.isEmpty(fileType)) {
				return false;
			}
			
			if(allowedFileTypes == null) {
				initFileTypes();
			}
			
			return allowedFileTypes.get(fileType);		
		}
		
		private static void initFileTypes() {
			allowedFileTypes = new HashMap<String, Boolean>();
			allowedFileTypes.put("image/png", true);
			allowedFileTypes.put("image/jpg", true);
			allowedFileTypes.put("image/jpeg", true);
			allowedFileTypes.put("image/gif", true);
		}
	}


