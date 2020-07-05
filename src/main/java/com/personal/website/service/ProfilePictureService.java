package com.personal.website.service;

import com.personal.website.entity.ProfilePictureEntity;
import com.personal.website.exception.FileStorageException;
import com.personal.website.model.ProfilePictureModel;
import com.personal.website.repository.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ProfilePictureService
{
    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    public ProfilePictureEntity saveProfilePicture(MultipartFile imageFile)
    {
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());

        try
        {
            // Check if the file's name contains invalid characters
            if (fileName.contains(".."))
            {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //keep only one profile picture in the database,delete previous ones and set the current one

            profilePictureRepository.deleteAll();

            return profilePictureRepository.save(
                    ProfilePictureEntity.builder()
                                        .fileName(imageFile.getName())
                                        .fileType(imageFile.getContentType())
                                        .data(compressBytes(imageFile.getBytes()))
                                        .build());
        }
        catch (FileStorageException | IOException ex)
        {
            throw new FileStorageException("could not save file");
        }

    }

    //compress image before saving to database
    public static byte[] compressBytes(byte[] data)
    {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished())
        {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try
        {
            outputStream.close();
        }
        catch (IOException e)
        {
        }
        return outputStream.toByteArray();
    }

    //decompress in returning the image

    public static byte[] decompressBytes(byte[] data)
    {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try
        {
            while (!inflater.finished())
            {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        }
        catch (IOException ioe)
        {
        }
        catch (DataFormatException e)
        {

        }
        return outputStream.toByteArray();
    }

}

