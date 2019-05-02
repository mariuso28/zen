package org.zen.rating.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Base64;

public class RatingImageImport {

	private static final Logger log = Logger.getLogger(RatingImageImport.class);
	
	private static byte[] loadImageBytes(String number) throws IOException
	{
		String suffix = ".jpeg";
		if (number.equals("0"))
			suffix = ".png";
		Path path = Paths.get("/home/pmk/workspace/zen/WebContent/img/" + number + suffix);
	//	log.info("Loading : " + path);
	    return Files.readAllBytes(path);
	}
	
	public static String loadImage(String number)
	{
		try {
			byte[] img = loadImageBytes(number);
			img = Base64.encode(img);
			return new String(img,"UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}

	}
	
}
