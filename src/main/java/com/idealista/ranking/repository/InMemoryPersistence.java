package com.idealista.ranking.repository;

import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.repository.PictureVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@Slf4j
public class InMemoryPersistence {

    private List<AdVO> ads;
    private List<PictureVO> pictures;

    public InMemoryPersistence() {
        ads = new ArrayList<AdVO>();
        ads.add(new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new AdVO(3, "CHALET", "", Arrays.asList(2), 300, null, null, null));
        ads.add(new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new AdVO(5, "FLAT", "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
        ads.add(new AdVO(6, "GARAGE", "", Arrays.asList(6), 300, null, null, null));
        ads.add(new AdVO(7, "GARAGE", "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<PictureVO>();
        pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", "SD"));
        pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", "HD"));
        pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", "SD"));
        pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", "HD"));
        pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", "SD"));
        pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", "SD"));
        pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", "SD"));
        pictures.add(new PictureVO(8, "http://www.idealista.com/pictures/8", "HD"));
    }

    public List<AdVO> getAllAds() {
        return ads;
    }

    public Optional<AdVO> getAdById(Integer id) {
        return ads.stream().filter(a -> a.getId().equals(id)).findAny();
    }

    public List<AdVO> getAdsIn(List<Integer> ids) {
        return ads.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());
    }

    public void upsertAd(AdVO ad) {
        int index = getAdIndexById(ad.getId(), ads);

        if (index != -1) {
            log.info("Found ad: " + ad + " in position:" + index + ", updating...");
            ads.set(index, ad);
        } else {
            log.info("Ad: " + ad + " not found, inserting...");
            ads.add(ad);
        }
    }

    public List<PictureVO> getAllPictures() {
        return pictures;
    }

    public Optional<PictureVO> getPictureById(Integer id) {
        return pictures.stream().filter(a -> a.getId().equals(id)).findAny();
    }

    public List<PictureVO> getPicturesIn(List<Integer> ids) {
        return pictures.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());
    }

    public void upsertPicture(PictureVO pictureVO) {
        int index = getPicIndexById(pictureVO.getId(), pictures);

        if (index != -1) {
            log.info("Found picture: " + pictureVO + " in position:" + index + ", updating...");
            pictures.set(index, pictureVO);
        } else {
            log.info("Picture: " + pictureVO + " not found, inserting...");
            pictures.add(pictureVO);
        }
    }

    private Integer getAdIndexById(Integer id, List<AdVO> ads) {
        return IntStream.range(0, ads.size())
                .filter(i -> ads.get(i).getId().equals(id))
                .findFirst().orElse(-1);
    }

    private Integer getPicIndexById(Integer id, List<PictureVO> pictures) {
        return IntStream.range(0, pictures.size())
                .filter(i -> pictures.get(i).getId().equals(id))
                .findFirst().orElse(-1);
    }
}
