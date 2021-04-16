package me.rayzr522.decoheads.gui;

import me.rayzr522.decoheads.Category;
import me.rayzr522.decoheads.DecoHeads;
import me.rayzr522.decoheads.data.Head;
import me.rayzr522.decoheads.gui.system.Button;
import me.rayzr522.decoheads.gui.system.Dimension;
import me.rayzr522.decoheads.gui.system.GUI;
import me.rayzr522.decoheads.gui.system.Label;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rayzr
 */
public class CategoryGUI extends GUI {

    private DecoHeads plugin = DecoHeads.getInstance();

    public CategoryGUI(Player player) {
        super(player, DecoHeads.getInstance().tr(false, "gui.categories.title"), 5);
        init();
    }

    private void init() {
        addComponent(Label.background(0, 0, 9, 5));
        DecoHeads plugin = DecoHeads.getInstance();

        for (Category category : Category.values()) {
            String categoryName = plugin.tr(false, String.format("category.%s", category.getKey()));

            List<Head> heads = this.plugin.getHeadManager().getHeads().stream()
                    .filter(head -> head.getCategory() == category)
                    .collect(Collectors.toList());
            Head head = heads.get((int) (Math.random() * (heads.size() - 1)));

            Button categoryButton = new Button(
                    head.getItem(),
                    Dimension.ONE,
                    category.getPosition(),
                    e -> new HeadsGUI(e.getPlayer(), 1, h -> h.getCategory() == category, this).render(),
                    plugin.tr(false, "button.categories.category.name", categoryName),
                    plugin.tr(false, "button.categories.category.lore").split("\n")
            );

            addComponent(categoryButton);
        }
    }

}
