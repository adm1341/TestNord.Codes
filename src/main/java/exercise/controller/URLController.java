package exercise.controller;

import exercise.URLNotFoundException;
import exercise.repository.URLRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/short")
public class URLController {

    @Autowired
    private URLRepository urlRepository;


    @GetMapping(path = "/{shortUrl}")
    public URL getFullUrlByShort(@PathVariable String shortUrl) {

        exercise.model.URL urlBase = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));

        URL urlReturn = null;
        try {
            urlReturn = new URL(urlBase.getFullUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        urlBase.setNumberClick(urlBase.getNumberClick() + 1);
        urlRepository.save(urlBase);

        return urlReturn;

    }
    @GetMapping(path = "/{shortUrl}/info")
    public exercise.model.URL getInfoByShort(@PathVariable String shortUrl) {

        exercise.model.URL urlBase = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));
        return urlBase;

    }

    @PostMapping(path = "/")
    public URL createShortURL(@RequestBody URL url) {

        exercise.model.URL urlBase = new exercise.model.URL();

        urlBase.setFullUrl(String.valueOf(url));

        String shortUrl = RandomStringUtils.randomAlphanumeric(7);
        urlBase.setShortUrl(shortUrl);
        urlBase.setNumberClick(0);

        urlRepository.save(urlBase);
        URL urlReturn = null;
        try {
            urlReturn = new URL("http://www.example.com/" + shortUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlReturn;
    }

    @DeleteMapping(path = "/{shortUrl}")
    public void deleteShortURL(@PathVariable String shortUrl) {

        exercise.model.URL urlBase = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));
        urlRepository.delete(urlBase);
    }
}
