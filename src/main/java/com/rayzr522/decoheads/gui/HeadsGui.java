/**
 * 
 */
package com.rayzr522.decoheads.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.decoheads.DecoHeads;
import com.rayzr522.decoheads.Head;
import com.rayzr522.decoheads.gui.system.Button;
import com.rayzr522.decoheads.gui.system.Dimension;
import com.rayzr522.decoheads.gui.system.Gui;
import com.rayzr522.decoheads.gui.system.Label;

/**
 * @author Rayzr
 *
 */
public class HeadsGui extends Gui {

    /**
     * How many heads are on a page
     */
    public static final int  WIDTH           = 7;
    public static final int  HEIGHT          = 3;
    public static final int  OFFSET_X        = 1;
    public static final int  OFFSET_Y        = 1;
    public static final int  SIZE            = WIDTH * HEIGHT;

    private static ItemStack BUTTON_ENABLED  = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private static ItemStack BUTTON_DISABLED = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

    private DecoHeads        plugin;
    private int              page;
    private String           filter;

    /**
     * @param player the player to open the GUI for
     * @param page the page of the heads GUI
     * @param filter the filter
     */
    public HeadsGui(Player player, int page, String filter) {
        super(player, DecoHeads.getInstance().tr("gui.title", page), 6);
        this.plugin = DecoHeads.getInstance();
        this.page = page;
        this.filter = filter;

        init();
        System.out.println(getInventory().getClass().getCanonicalName());
    }

    private Button makeButton(String label, Dimension position, boolean enabled) {
        return new Button((enabled ? BUTTON_ENABLED : BUTTON_DISABLED).clone(), Dimension.ONE, position, e -> {
        }, (enabled ? "&a" : "&c") + "&l" + label);
    }

    private void refresh() {
        setComponents(new ArrayList<>());
        init();
        render();
    }

    /**
     * Gets all the heads (with the given filter)
     * 
     * @return All the heads, filtered with the filter from
     *         {@link #HeadsGui(Player, int, String)}
     */
    public List<Head> getHeads() {
        return plugin.getHeadManager().searchHeads(filter);
    }

    /**
     * 
     */
    private void init() {

        List<Head> filteredHeads = getHeads();

        addComponent(new Label(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), new Dimension(9, 1), new Dimension(0, 5), " "));

        Button previousPage = makeButton(plugin.tr("button.previous-page"), new Dimension(2, 5), page > 1);
        Button nextPage = makeButton(DecoHeads.getInstance().tr("button.next-page"), new Dimension(6, 5), page < plugin.getHeadManager().maxPages(filteredHeads));

        previousPage.setClickHandler(e -> {
            if (page <= 1) {
                return;
            }
            page--;
            refresh();
        });

        nextPage.setClickHandler(e -> {
            if (page >= plugin.getHeadManager().maxPages(filteredHeads)) {
                return;
            }
            page++;
            refresh();
        });

        addComponent(nextPage);
        addComponent(previousPage);

        if (filteredHeads.size() < 1) {
            return;
        }

        int offset = (page - 1) * SIZE;

        for (int i = 0; i < SIZE; i++) {

            int pos = offset + i;
            if (pos >= filteredHeads.size()) {
                break;
            }

            Head head = filteredHeads.get(pos);
            addComponent(new HeadButton(head, new Dimension(OFFSET_X + i % WIDTH, OFFSET_Y + i / WIDTH)));

        }

    }

}
