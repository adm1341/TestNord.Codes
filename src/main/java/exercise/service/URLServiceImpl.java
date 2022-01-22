package exercise.service;

import exercise.exception.URLGoneException;
import exercise.exception.URLNotFoundException;
import exercise.repository.URLRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class URLServiceImpl {
    @Autowired
    private URLRepository urlRepository;

    public URL getFullUrlByShort(String shortUrl) {

        exercise.model.URL urlBase = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));

        URL urlReturn = null;
        try {
            urlReturn = new URL(urlBase.getFullUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        long secondsDiff = (getDateNow().getTime() - urlBase.getDateCreate().getTime()) / 1000;

        if (secondsDiff > urlBase.getTimeLive()) {
            throw new URLGoneException("URL gone");
        }


        urlBase.setNumberClick(urlBase.getNumberClick() + 1);
        urlRepository.save(urlBase);

        return urlReturn;

    }

    public exercise.model.URL getInfoByShort(String shortUrl) {

        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));

    }

    public URL createShortURL(URL url, Integer timeLive) {

        exercise.model.URL urlBase = new exercise.model.URL();

        urlBase.setFullUrl(String.valueOf(url));

        String shortUrl = RandomStringUtils.randomAlphanumeric(7);
        urlBase.setShortUrl(shortUrl);
        urlBase.setNumberClick(0);

        urlBase.setTimeLive(timeLive);
        urlBase.setDateCreate(getDateNow());

        urlRepository.save(urlBase);
        URL urlReturn = null;
        try {
            urlReturn = new URL("http://www.example.com/" + shortUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlReturn;
    }

    public void deleteShortURL(String shortUrl) {

        exercise.model.URL urlBase = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new URLNotFoundException("URL not found"));
        urlRepository.delete(urlBase);
    }

    private Date getDateNow() {
        LocalDateTime todayLocal = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        Date todayUtil = Date.from(todayLocal.atZone(ZoneId.of("Europe/Moscow")).toInstant());
        return todayUtil;
    }
}
