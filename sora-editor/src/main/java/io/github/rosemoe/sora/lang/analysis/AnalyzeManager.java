/*
 *    sora-editor - the awesome code editor for Android
 *    https://github.com/Rosemoe/sora-editor
 *    Copyright (C) 2020-2022  Rosemoe
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 *
 *     Please contact Rosemoe by email 2073412493@qq.com if you need
 *     additional information or have any questions
 */
package io.github.rosemoe.sora.lang.analysis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.widget.CodeEditor;

/**
 * AnalyzeManager receives text updates and do its work to start lexers/parsers to analyze the code
 * so that we can provide syntax-highlighting and exact auto-completion.
 *
 * @author Rosemoe
 */
public interface AnalyzeManager {

    /**
     * Set the span receiver of the manager.
     *
     * This is called when the {@link io.github.rosemoe.sora.lang.Language} is going to be used by
     * an editor. It will also be called when the instance is no longer used.
     * Make sure you check the exact receiver at the time you send results to it.
     * Thus, you should save the instance at your side.
     */
    void setReceiver(@Nullable StyleReceiver receiver);

    /**
     * Called when new text is set in editor by either {@link CodeEditor#setText(CharSequence)}
     * or {@link CodeEditor#setText(CharSequence, Bundle).}
     * @param content New text, read-only. Accesses to it are not validated. It is not recommended saving
     *                this object for reading. You can make a copy of the text and update it. But for
     *                memory saving, you may want to store it.
     * @param extraArguments  Arguments set by {@link CodeEditor#setText(CharSequence, Bundle)}
     */
    void reset(@NonNull ContentReference content, @NonNull Bundle extraArguments);

    /**
     * The {@link ContentReference} set by {@link #reset(ContentReference, Bundle)} has inserted
     * text with the given position and text.
     *
     * @param start       The insertion start
     * @param end         The insertion end
     * @param insertedContent The content inserted
     */
    void insert(CharPosition start, CharPosition end, CharSequence insertedContent);

    /**
     * The {@link ContentReference} set by {@link #reset(ContentReference, Bundle)} has deleted
     *  text with the given position and text.
     *
     * @param start      The deletion start
     * @param end        The deletion end
     * @param deletedContent The text deleted. Generated by {@link Content} object.
     */
    void delete(CharPosition start, CharPosition end, CharSequence deletedContent);

    /**
     * Rerun the analysis forcibly
     */
    void rerun();

    /**
     * Destroy the manager. Release any resources held.
     * Make sure that you will not call the receiver anymore.
     */
    void destroy();

}
