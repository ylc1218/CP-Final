package cloud.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileMgr {
	
	public static String saveArticle(String fileName, String content) throws IOException {		
		Path tempFilePath = Files.createTempFile(null, ".txt");
		//System.out.println(tempFilePath.toString());
		
		OutputStream os = new FileOutputStream(tempFilePath.toString());

		os.write(content.getBytes());
		os.close();
		
		File file = tempFilePath.toFile();
		AmazonS3 s3client = AwsManager.getS3Client();
		s3client.putObject(new PutObjectRequest(Header.S3_BUCKET_NAME, Header.S3_NEWS_TXT_DIR+fileName, file));		
		Files.delete(tempFilePath);
		return Header.S3_PREFIX+Header.S3_BUCKET_NAME+"/"+Header.S3_NEWS_TXT_DIR+fileName;
	}
}
