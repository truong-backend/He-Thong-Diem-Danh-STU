package vn.diemdanh.hethong.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaceRekognitionService {
    private final AmazonRekognition rekognitionClient;

    public Optional<String> detectStudentFaceId(MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        Image image = new Image().withBytes(ByteBuffer.wrap(imageBytes));

        SearchFacesByImageRequest request = new SearchFacesByImageRequest()
                .withCollectionId("diem-danh-stu")
                .withImage(image)
                .withFaceMatchThreshold(90F)
                .withMaxFaces(1);
        SearchFacesByImageResult result = rekognitionClient.searchFacesByImage(request);
        System.out.println("Số match được: " + result.getFaceMatches().size());
        List<FaceMatch> matches = result.getFaceMatches();
        if(!matches.isEmpty()) {
            return Optional.of(matches.get(0).getFace().getExternalImageId().replace(".jpg", ""));
        }
        else{
            return Optional.empty();
        }
    }
}
