/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.toolbar = 'Full';
 
	config.toolbar_Full =
	[
		['Source'],
		['Undo','Redo','-','Find','Replace'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['NumberedList','BulletedList'],
		'/',
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['Link','Unlink'],
		['Image','Table','HorizontalRule','SpecialChar'],
		['Format']
	];
	config.filebrowserImageUploadUrl = './misEventAdd.htm?type=img';
	config.font_defaultLabel = '微软雅黑';
	config.font_names = '微软雅黑;宋休;楷体;隶书;Arial;Times New Roman;Verdana;Tahoma;Courier New';
	config.height = 500;
};
