<?php
class Achievement_model extends CI_Model {

	public function __construct()
	{
		$this->load->database();
	}

	public function get_achievements($user_id = FALSE){

		$this->db->from('user_achievement');
		$this->db->select('achievement.id, name, description, icon, color');
		$this->db->join('achievement', 'achievement.id = id_achievement');
		$this->db->where('id_user',$user_id);

		return $this->db->get()->result();

	}
}