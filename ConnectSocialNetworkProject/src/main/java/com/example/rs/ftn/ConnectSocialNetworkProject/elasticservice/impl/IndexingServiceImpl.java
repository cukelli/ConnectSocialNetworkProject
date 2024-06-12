package com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.impl;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.FileService;
import com.example.rs.ftn.ConnectSocialNetworkProject.elasticservice.IndexingService;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.LoadingException;
import com.example.rs.ftn.ConnectSocialNetworkProject.exception.StorageException;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.GroupIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexmodel.PostIndex;
import com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository.GroupIndexRepository;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Group;
import com.example.rs.ftn.ConnectSocialNetworkProject.model.entity.Post;
import com.example.rs.ftn.ConnectSocialNetworkProject.repository.GroupRepo;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final GroupIndexRepository groupIndexRepository;

    private final GroupRepo groupRepository;

    private final FileService fileService;

    private final LanguageDetector languageDetector;


    @Override
    @Transactional
    public String indexGroupDocument(MultipartFile documentFile) {
        var newGroup = new Group();
        var newGroupIndex = new GroupIndex();

        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        newGroupIndex.setName(title);
        newGroup.setName(title);

        var documentContent = extractDocumentContent(documentFile);
        if (detectLanguage(documentContent).equals("SR")) {
            newGroupIndex.setDescription(documentContent);
        } else {
            newGroupIndex.setDescription(documentContent);
        }
        newGroup.setDescription(documentContent);

        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
        newGroup.setSuspendedReason(null);
        newGroup.setCreatedAt(LocalDate.now());
        newGroup.setSuspended(false);
        newGroup.setDeleted(false);

        var savedGroup = groupRepository.save(newGroup);
        newGroupIndex.setCreatedAt(newGroup.getCreatedAt());
        newGroupIndex.setSuspendedReason(newGroup.getSuspendedReason());
        newGroupIndex.setSuspended(false);
        newGroupIndex.setSuspendedReason(null);
        newGroupIndex.setDatabaseId(savedGroup.getId());
        groupIndexRepository.save(newGroupIndex);

        return serverFilename;
    }


//    @Override
//    @Transactional
//    public String indexPostDocument(MultipartFile documentFile) {
//        var newPost = new Post();
//        var newPostIndex = new PostIndex();
//
//        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
//        newPostIndex.setTitle(title);
//        newPost.setTitle(title);
//
//        var documentContent = extractDocumentContent(documentFile);
//        newPostIndex.setContent(documentContent);
//        newPost.setContent(documentContent);
//
//        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
//        newPostIndex.setId(serverFilename);
//        // Pretpostavimo da Post ima polje za čuvanje serverFilename ako je potrebno
//        // newPost.setServerFilename(serverFilename);
//
//        newPost.setCreationDate(LocalDateTime.now());
//        newPostIndex.setCreationDate(LocalDateTime.now());
//
//        // Podesite ostale atribute Post i PostIndex entiteta
//        newPost.setDeleted(false);
//        newPostIndex.setDeleted(false);
//
//        // Pretpostavimo da User entitet i Group entitet dolaze iz konteksta ili drugog izvora
//        var user = getCurrentUser(); // Metoda za dobijanje trenutnog korisnika
//        var group = getCurrentGroup(); // Metoda za dobijanje trenutne grupe
//
//        newPost.setUser(user);
//        newPostIndex.setUser(user.getUsername());
//
//        newPost.setGroupPosted(group);
//        newPostIndex.setGroupPosted(group != null ? group.getGroupId() : null);
//
//        // Pretpostavimo da je lista imagePaths dobijena iz fajla ili drugog izvora
//        var imagePaths = extractImagePaths(documentFile);
//        newPost.setImagePaths(imagePaths);
//        newPostIndex.setImagePaths(imagePaths.stream().map(Image::getPath).collect(Collectors.toList()));
//
//        var savedPost = postRepository.save(newPost);
//        newPostIndex.setId(savedPost.getPostId().toString());
//        postIndexRepository.save(newPostIndex);
//
//        return serverFilename;
//    }

    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                    Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
                !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }
}