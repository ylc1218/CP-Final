package cloud.project;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AwsManager {
	private static AWSCredentials credentials = new BasicAWSCredentials(Header.AWS_ACCESS_KEY, Header.AWS_SECRET_KEY);
	private static AmazonS3 s3Client = new AmazonS3Client(credentials);

	public static AmazonS3 getS3Client(){
		return s3Client;
	}

}
