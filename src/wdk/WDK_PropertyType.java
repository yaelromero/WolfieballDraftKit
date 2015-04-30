package wdk;

/**
 * These are properties that are to be loaded from properties.xml. They
 * will provide custom labels and other UI details for our Wolfieball Draft Kit
 * application. The reason for doing this is to swap out UI text and icons
 * easily without having to touch our code. It also allows for language
 * independence.
 * 
 * @author Yael
 */

public enum WDK_PropertyType {
        // LOADED FROM properties.xml
        PROP_APP_TITLE,
        
        // APPLICATION ICONS
        NEW_DRAFT_ICON,
        LOAD_DRAFT_ICON,
        SAVE_DRAFT_ICON,
        EXPORT_PAGE_ICON,
        EXIT_ICON,
        ADD_ICON,
        MINUS_ICON,
        EDIT_ICON,
        DRAFT_SCREEN_ICON,
        HOME_ICON,
        MLB_TEAMS_ICON,
        PLAYERS_ICON,
        STANDINGS_ICON,
        
        // APPLICATION TOOLTIPS FOR BUTTONS
        NEW_DRAFT_TOOLTIP,
        LOAD_DRAFT_TOOLTIP,
        SAVE_DRAFT_TOOLTIP,
        EXPORT_PAGE_TOOLTIP,
        EXIT_TOOLTIP,
        HOME_TOOLTIP,
        DRAFT_SCREEN_TOOLTIP,
        MLB_TEAMS_TOOLTIP,
        PLAYERS_TOOLTIP,
        STANDINGS_TOOLTIP, 
        ADD_PLAYER_TOOLTIP,
        REMOVE_PLAYER_TOOLTIP,
        ADD_TEAM_TOOLTIP,
        REMOVE_TEAM_TOOLTIP,
        EDIT_TEAM_TOOLTIP,

        // FOR DRAFT EDIT WORKSPACE
        FANTASY_HEADING_LABEL,
        PLAYERS_HEADING_LABEL,
        STANDINGS_HEADING_LABEL,
        STARTING_LINEUP_LABEL,
        DRAFT_HEADING_LABEL,
        MLB_TEAMS_HEADING_LABEL,
        SEARCH_LABEL,
        DRAFT_NAME_LABEL,
        SELECT_TEAM_LABEL,
        
        // ERROR DIALOG MESSAGES
        CLOSE_BUTTON_TEXT,
        
        // AND VERIFICATION MESSAGES
        NEW_DRAFT_CREATED_MESSAGE,
        DRAFT_LOADED_MESSAGE,
        DRAFT_SAVED_MESSAGE,
        SITE_EXPORTED_MESSAGE,
        SAVE_UNSAVED_WORK_MESSAGE,
        REMOVE_ITEM_MESSAGE
}
