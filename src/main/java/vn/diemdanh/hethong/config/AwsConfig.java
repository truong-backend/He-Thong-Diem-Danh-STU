package vn.diemdanh.hethong.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Bean
    public AmazonRekognition rekognitionClient() {
        return AmazonRekognitionClientBuilder.standard()
                .withRegion(Regions.AP_SOUTHEAST_1) // vùng phù hợp
                .build(); // tự động lấy credentials từ ~/.aws/credentials
    }

    @Bean
    public AmazonS3Client amazonS3() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build(); // tự động lấy credentials
    }
}