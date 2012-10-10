/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PositionScannerSinglePredicate extends PositionScanner
{
    protected final LineCheckPredicate predicate;

    public PositionScannerSinglePredicate(CheckPatternSet patternSet, LineIteratorFactory[] lineIteratorFactories,
            Board board)
    {
        super(lineIteratorFactories, board);
        this.predicate = new LineCheckPredicate(patternSet);
    }
}