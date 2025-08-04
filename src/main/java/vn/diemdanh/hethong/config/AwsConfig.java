package vn.diemdanh.hethong.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsConfig.AwsProperties.class)
public class AwsConfig {

    private final AwsProperties awsProperties;

    public AwsConfig(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public AmazonRekognition rekognitionClient() {
        validateCredentials();

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
                awsProperties.getCredentials().getAccessKey(),
                awsProperties.getCredentials().getSecretKey()
        );

        return AmazonRekognitionClientBuilder.standard()
                .withRegion(awsProperties.getRegion().getStatic())
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    @Bean
    public AmazonS3 amazonS3() {
        validateCredentials();

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
                awsProperties.getCredentials().getAccessKey(),
                awsProperties.getCredentials().getSecretKey()
        );

        return AmazonS3ClientBuilder.standard()
                .withRegion(awsProperties.getRegion().getStatic())
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    private void validateCredentials() {
        if (awsProperties.getCredentials().getAccessKey() == null ||
                awsProperties.getCredentials().getAccessKey().isEmpty() ||
                awsProperties.getCredentials().getSecretKey() == null ||
                awsProperties.getCredentials().getSecretKey().isEmpty()) {
            throw new IllegalStateException("AWS credentials not found. Please set cloud.aws.credentials.access-key and cloud.aws.credentials.secret-key");
        }
    }

    @ConfigurationProperties(prefix = "cloud.aws")
    public static class AwsProperties {
        private Credentials credentials = new Credentials();
        private Region region = new Region();

        // Getters and setters
        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public Region getRegion() {
            return region;
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public static class Credentials {
            private String accessKey;
            private String secretKey;

            public String getAccessKey() {
                return accessKey;
            }

            public void setAccessKey(String accessKey) {
                this.accessKey = accessKey;
            }

            public String getSecretKey() {
                return secretKey;
            }

            public void setSecretKey(String secretKey) {
                this.secretKey = secretKey;
            }
        }

        public static class Region {
            private String static_ = "ap-southeast-1";

            public String getStatic() {
                return static_;
            }

            public void setStatic(String static_) {
                this.static_ = static_;
            }
        }
    }
}