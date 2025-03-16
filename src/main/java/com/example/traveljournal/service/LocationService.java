package com.example.traveljournal.service;

import com.example.traveljournal.domain.TravelLocation;
import com.example.traveljournal.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Page<TravelLocation> getAllLocations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return locationRepository.findAll(pageable);
    }

    public TravelLocation getLocation(String id) {
        return locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    public TravelLocation createLocation(TravelLocation location) {
        return locationRepository.save(location);
    }

    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }

    public String uploadPhoto(String id, MultipartFile file) {
        TravelLocation location = getLocation(id);
        String photoUrl = photoFunction.apply(id, file);
        location.setPhotoUrl(photoUrl);
        locationRepository.save(location);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = fileName ->
            Optional.of(fileName)
                    .filter(name -> name.contains("."))
                    .map(name -> "." + name.substring(fileName.lastIndexOf(".")  + 1))
                    .orElse(".jpg");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        try {
            Path fileStorageLocation = Paths.get(System.getProperty("user.home") + "/Downloads/uploads")
                    .toAbsolutePath()
                    .normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectory(fileStorageLocation);
            }
            String fileName = id + fileExtension.apply(image.getOriginalFilename());
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/locations/image/" + id + fileName)
                    .toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to upload photo");
        }
    };
}
