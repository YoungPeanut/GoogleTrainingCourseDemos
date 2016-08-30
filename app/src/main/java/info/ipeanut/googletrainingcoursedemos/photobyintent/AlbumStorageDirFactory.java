package info.ipeanut.googletrainingcoursedemos.photobyintent;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
