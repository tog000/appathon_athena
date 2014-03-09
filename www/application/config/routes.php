<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
/*
| -------------------------------------------------------------------------
| URI ROUTING
| -------------------------------------------------------------------------
| This file lets you re-map URI requests to specific controller functions.
|
| Typically there is a one-to-one relationship between a URL string
| and its corresponding controller class/method. The segments in a
| URL normally follow this pattern:
|
|	example.com/class/method/id/
|
| In some instances, however, you may want to remap this relationship
| so that a different class/function is called than the one
| corresponding to the URL.
|
| Please see the user guide for complete details:
|
|	http://codeigniter.com/user_guide/general/routing.html
|
| -------------------------------------------------------------------------
| RESERVED ROUTES
| -------------------------------------------------------------------------
|
| There area two reserved routes:
|
|	$route['default_controller'] = 'welcome';
|
| This route indicates which controller class should be loaded if the
| URI contains no data. In the above example, the "welcome" class
| would be loaded.
|
|	$route['404_override'] = 'errors/page_missing';
|
| This route will tell the Router what URI segments to use if those provided
| in the URL cannot be matched to a valid route.
|
*/

$route['default_controller'] = "question";
$route['404_override'] = '';
$route['get_scoreboard'] = 'user/get_scoreboard';
$route['achievements/(:any)'] = 'achievement/get_achievements/$1';
$route['question/create'] = 'question/create';
$route['question/(:any)'] = 'question/index/$1';
$route['question_answered_test/(:any)'] = 'question/question_answered_test/$1';
$route['question_answered'] = 'question/question_answered';
$route['question_for_user/(:any)'] = 'question/question_for_user/$1';
$route['register_user/(:any)'] = 'user/register_user/$1';
$route['user'] = 'user';
//$route['question/create'] = 'question/create';


/* End of file routes.php */
/* Location: ./application/config/routes.php */