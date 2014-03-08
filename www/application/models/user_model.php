<?php
class User_model extends CI_Model {

	public function __construct()
	{
		$this->load->database();
	}

	public function get_user($id = FALSE)
	{
		if ($id === FALSE)
		{
			$query = $this->db->get('user');
			return $query->result();
		}

		$query = $this->db->get('user',array("id"=>$id));
		return $query->result();
	}

	public function set_question()
	{

		$this->load->helper('url');

		$data = array(
			'name' => $this->input->post('name'),
			'avatar' => $this->input->post('avatar'),
			'experience' => 0,
			);

		$this->db->insert('user', $data);

		$insert_id = $this->db->insert_id();

		return $insert_id;

	}
}