package cloud.project;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class AwsManager {
	private static AWSCredentials credentials = new BasicAWSCredentials(Header.AWS_ACCESS_KEY, Header.AWS_SECRET_KEY);
	private static AmazonS3 s3Client = new AmazonS3Client(credentials);
	private static AmazonSQS sqsClient = new AmazonSQSClient(credentials);

	public static AmazonS3 getS3Client(){
		return s3Client;
	}
	
	public static AmazonSQS getSqsClient(){
		return sqsClient;
	}

}
