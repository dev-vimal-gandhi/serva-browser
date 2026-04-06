package com.servalabs.browser.utils

import android.content.res.Configuration
import com.servalabs.browser.utils.ServaWallpaperDictionary

// Helpers for managing Serva wallpapers

object ServaWallpaperUtils {

    /**
     * Get a wallpaper's drawable resource
     *
     * @param wallpaperName The name of the wallpaper
     * @param orientation The desired wallpaper orientation
     */
    fun getWallpaperDrawable(
        wallpaperName: String,
        orientation: Int
    ): Int {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return getWallpaperDrawableLandscape(wallpaperName)
        } else {
            return getWallpaperDrawablePortrait(wallpaperName)
        }
    }

    /**
     * Get a wallpaper's landscape drawable resource
     *
     * @param wallpaperName The name of the wallpaper
     */
    internal fun getWallpaperDrawableLandscape(
        wallpaperName: String
    ): Int {
        if (wallpaperName == ServaWallpaperDictionary.algaeWallpaperName) {
            return ServaWallpaperDictionary.algaeLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.blackWallpaperName) {
            return ServaWallpaperDictionary.blackLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.colorfulBubblesWallpaperName) {
            return ServaWallpaperDictionary.colorfulBubblesLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.darkDuneWallpaperName) {
            return ServaWallpaperDictionary.darkDuneLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.deepBlueWallpaperName) {
            return ServaWallpaperDictionary.deepBlueLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.duneWallpaperName) {
            return ServaWallpaperDictionary.duneLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.fireyRedWallpaperName) {
            return ServaWallpaperDictionary.fireyRedLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.redWallpaperName) {
            return ServaWallpaperDictionary.redLandscapeDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.whiteWallpaperName) {
            return ServaWallpaperDictionary.whiteLandscapeDrawable
        } else {
            return ServaWallpaperDictionary.pinkLandscapeDrawable
        }
    }

    /**
     * Get a wallpaper's portrait drawable resource
     *
     * @param wallpaperName The name of the wallpaper
     */
    internal fun getWallpaperDrawablePortrait(
        wallpaperName: String
    ): Int {
        if (wallpaperName == ServaWallpaperDictionary.algaeWallpaperName) {
            return ServaWallpaperDictionary.algaePortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.blackWallpaperName) {
            return ServaWallpaperDictionary.blackPortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.colorfulBubblesWallpaperName) {
            return ServaWallpaperDictionary.colorfulBubblesPortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.darkDuneWallpaperName) {
            return ServaWallpaperDictionary.darkDunePortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.deepBlueWallpaperName) {
            return ServaWallpaperDictionary.deepBluePortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.duneWallpaperName) {
            return ServaWallpaperDictionary.dunePortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.fireyRedWallpaperName) {
            return ServaWallpaperDictionary.fireyRedPortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.redWallpaperName) {
            return ServaWallpaperDictionary.redPortraitDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.whiteWallpaperName) {
            return ServaWallpaperDictionary.whitePortraitDrawable
        } else {
            return ServaWallpaperDictionary.pinkPortraitDrawable
        }
    }

    /**
     * Get a wallpaper's thumbnail drawable resource
     *
     * @param wallpaperName The name of the wallpaper
     */
    fun getWallpaperDrawableThumbnail(
        wallpaperName: String
    ): Int {
        if (wallpaperName == ServaWallpaperDictionary.algaeWallpaperName) {
            return ServaWallpaperDictionary.algaeThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.blackWallpaperName) {
            return ServaWallpaperDictionary.blackThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.colorfulBubblesWallpaperName) {
            return ServaWallpaperDictionary.colorfulBubblesThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.darkDuneWallpaperName) {
            return ServaWallpaperDictionary.darkDuneThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.deepBlueWallpaperName) {
            return ServaWallpaperDictionary.deepBlueThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.duneWallpaperName) {
            return ServaWallpaperDictionary.duneThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.fireyRedWallpaperName) {
            return ServaWallpaperDictionary.fireyRedThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.redWallpaperName) {
            return ServaWallpaperDictionary.redThumbnailDrawable
        } else if (wallpaperName == ServaWallpaperDictionary.whiteWallpaperName) {
            return ServaWallpaperDictionary.whiteThumbnailDrawable
        } else {
            return ServaWallpaperDictionary.pinkThumbnailDrawable
        }
    }
}
