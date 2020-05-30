package com.models.configApp.common.sectionStructures;

import com.models.section.Carousel;
import com.models.section.article.Article;
import com.models.section.cards.Cards;
import com.models.section.form.Form;
import lombok.Data;

@Data
public class SiteBody {
    Article article;
    Cards cards;
    Form form;
    Carousel carousel;
}
