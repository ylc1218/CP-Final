package cloud.project.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import cloud.project.AwsManager;
import cloud.project.Header;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class ImageHandler {
	public static String saveImage(String imageUrl, String fileName) throws IOException {
		URL url = new URL(imageUrl);
		Path tempFilePath = Files.createTempFile(null, ".jpg");
		System.out.println(tempFilePath.toString());
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(tempFilePath.toString());

		byte[] b = new byte[4096];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
		
		File file = tempFilePath.toFile();
		AmazonS3 s3client = AwsManager.getS3Client();
		s3client.putObject(new PutObjectRequest(Header.S3_BUCKET_NAME, Header.S3_NEWS_PHOTO_DIR+fileName, file));		
		Files.delete(tempFilePath);
		return Header.S3_PREFIX+Header.S3_BUCKET_NAME+"/"+Header.S3_NEWS_PHOTO_DIR+fileName;
	}
}
