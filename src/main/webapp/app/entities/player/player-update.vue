<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.player.home.createOrEditLabel"
          data-cy="PlayerCreateUpdateHeading"
          v-text="$t('proFootballClubApp.player.home.createOrEditLabel')"
        >
          Create or edit a Player
        </h2>
        <div>
          <div class="form-group" v-if="player.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="player.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.firstName')" for="player-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="player-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.player.firstName.$invalid, invalid: $v.player.firstName.$invalid }"
              v-model="$v.player.firstName.$model"
              required
            />
            <div v-if="$v.player.firstName.$anyDirty && $v.player.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.firstName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.lastName')" for="player-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="player-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.player.lastName.$invalid, invalid: $v.player.lastName.$invalid }"
              v-model="$v.player.lastName.$model"
              required
            />
            <div v-if="$v.player.lastName.$anyDirty && $v.player.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.gender')" for="player-gender">Gender</label>
            <select
              class="form-control"
              name="gender"
              :class="{ valid: !$v.player.gender.$invalid, invalid: $v.player.gender.$invalid }"
              v-model="$v.player.gender.$model"
              id="player-gender"
              data-cy="gender"
              required
            >
              <option
                v-for="gender in genderValues"
                :key="gender"
                v-bind:value="gender"
                v-bind:label="$t('proFootballClubApp.Gender.' + gender)"
              >
                {{ gender }}
              </option>
            </select>
            <div v-if="$v.player.gender.$anyDirty && $v.player.gender.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.gender.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.country')" for="player-country">Country</label>
            <select
              class="form-control"
              name="country"
              :class="{ valid: !$v.player.country.$invalid, invalid: $v.player.country.$invalid }"
              v-model="$v.player.country.$model"
              id="player-country"
              data-cy="country"
              required
            >
              <option
                v-for="country in countryValues"
                :key="country"
                v-bind:value="country"
                v-bind:label="$t('proFootballClubApp.Country.' + country)"
              >
                {{ country }}
              </option>
            </select>
            <div v-if="$v.player.country.$anyDirty && $v.player.country.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.country.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.age')" for="player-age">Age</label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="player-age"
              data-cy="age"
              :class="{ valid: !$v.player.age.$invalid, invalid: $v.player.age.$invalid }"
              v-model.number="$v.player.age.$model"
              required
            />
            <div v-if="$v.player.age.$anyDirty && $v.player.age.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.age.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.age.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.status')" for="player-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.player.status.$invalid, invalid: $v.player.status.$invalid }"
              v-model="$v.player.status.$model"
              id="player-status"
              data-cy="status"
              required
            >
              <option
                v-for="playerStatus in playerStatusValues"
                :key="playerStatus"
                v-bind:value="playerStatus"
                v-bind:label="$t('proFootballClubApp.PlayerStatus.' + playerStatus)"
              >
                {{ playerStatus }}
              </option>
            </select>
            <div v-if="$v.player.status.$anyDirty && $v.player.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.post')" for="player-post">Post</label>
            <select
              class="form-control"
              name="post"
              :class="{ valid: !$v.player.post.$invalid, invalid: $v.player.post.$invalid }"
              v-model="$v.player.post.$model"
              id="player-post"
              data-cy="post"
              required
            >
              <option v-for="post in postValues" :key="post" v-bind:value="post" v-bind:label="$t('proFootballClubApp.Post.' + post)">
                {{ post }}
              </option>
            </select>
            <div v-if="$v.player.post.$anyDirty && $v.player.post.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.post.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.totalPower')" for="player-totalPower"
              >Total Power</label
            >
            <input
              type="number"
              class="form-control"
              name="totalPower"
              id="player-totalPower"
              data-cy="totalPower"
              :class="{ valid: !$v.player.totalPower.$invalid, invalid: $v.player.totalPower.$invalid }"
              v-model.number="$v.player.totalPower.$model"
              required
            />
            <div v-if="$v.player.totalPower.$anyDirty && $v.player.totalPower.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.totalPower.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.totalPower.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.totalPower.max" v-text="$t('entity.validation.max', { max: 256 })">
                This field cannot be longer than 256 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.totalPower.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.goalkeeping')" for="player-goalkeeping"
              >Goalkeeping</label
            >
            <input
              type="number"
              class="form-control"
              name="goalkeeping"
              id="player-goalkeeping"
              data-cy="goalkeeping"
              :class="{ valid: !$v.player.goalkeeping.$invalid, invalid: $v.player.goalkeeping.$invalid }"
              v-model.number="$v.player.goalkeeping.$model"
              required
            />
            <div v-if="$v.player.goalkeeping.$anyDirty && $v.player.goalkeeping.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.goalkeeping.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.goalkeeping.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.goalkeeping.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.goalkeeping.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.defence')" for="player-defence">Defence</label>
            <input
              type="number"
              class="form-control"
              name="defence"
              id="player-defence"
              data-cy="defence"
              :class="{ valid: !$v.player.defence.$invalid, invalid: $v.player.defence.$invalid }"
              v-model.number="$v.player.defence.$model"
              required
            />
            <div v-if="$v.player.defence.$anyDirty && $v.player.defence.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.defence.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.defence.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.defence.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.defence.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.tackling')" for="player-tackling">Tackling</label>
            <input
              type="number"
              class="form-control"
              name="tackling"
              id="player-tackling"
              data-cy="tackling"
              :class="{ valid: !$v.player.tackling.$invalid, invalid: $v.player.tackling.$invalid }"
              v-model.number="$v.player.tackling.$model"
              required
            />
            <div v-if="$v.player.tackling.$anyDirty && $v.player.tackling.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.tackling.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.tackling.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.tackling.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.tackling.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.passing')" for="player-passing">Passing</label>
            <input
              type="number"
              class="form-control"
              name="passing"
              id="player-passing"
              data-cy="passing"
              :class="{ valid: !$v.player.passing.$invalid, invalid: $v.player.passing.$invalid }"
              v-model.number="$v.player.passing.$model"
              required
            />
            <div v-if="$v.player.passing.$anyDirty && $v.player.passing.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.passing.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.passing.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.passing.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.passing.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.teamSkill')" for="player-teamSkill">Team Skill</label>
            <input
              type="number"
              class="form-control"
              name="teamSkill"
              id="player-teamSkill"
              data-cy="teamSkill"
              :class="{ valid: !$v.player.teamSkill.$invalid, invalid: $v.player.teamSkill.$invalid }"
              v-model.number="$v.player.teamSkill.$model"
              required
            />
            <div v-if="$v.player.teamSkill.$anyDirty && $v.player.teamSkill.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.teamSkill.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.teamSkill.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.teamSkill.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.teamSkill.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.ballSkill')" for="player-ballSkill">Ball Skill</label>
            <input
              type="number"
              class="form-control"
              name="ballSkill"
              id="player-ballSkill"
              data-cy="ballSkill"
              :class="{ valid: !$v.player.ballSkill.$invalid, invalid: $v.player.ballSkill.$invalid }"
              v-model.number="$v.player.ballSkill.$model"
              required
            />
            <div v-if="$v.player.ballSkill.$anyDirty && $v.player.ballSkill.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.ballSkill.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.ballSkill.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.ballSkill.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.ballSkill.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.shooting')" for="player-shooting">Shooting</label>
            <input
              type="number"
              class="form-control"
              name="shooting"
              id="player-shooting"
              data-cy="shooting"
              :class="{ valid: !$v.player.shooting.$invalid, invalid: $v.player.shooting.$invalid }"
              v-model.number="$v.player.shooting.$model"
              required
            />
            <div v-if="$v.player.shooting.$anyDirty && $v.player.shooting.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.shooting.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.shooting.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.shooting.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.shooting.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.longShooting')" for="player-longShooting"
              >Long Shooting</label
            >
            <input
              type="number"
              class="form-control"
              name="longShooting"
              id="player-longShooting"
              data-cy="longShooting"
              :class="{ valid: !$v.player.longShooting.$invalid, invalid: $v.player.longShooting.$invalid }"
              v-model.number="$v.player.longShooting.$model"
              required
            />
            <div v-if="$v.player.longShooting.$anyDirty && $v.player.longShooting.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.longShooting.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.longShooting.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.longShooting.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.longShooting.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.dribbling')" for="player-dribbling">Dribbling</label>
            <input
              type="number"
              class="form-control"
              name="dribbling"
              id="player-dribbling"
              data-cy="dribbling"
              :class="{ valid: !$v.player.dribbling.$invalid, invalid: $v.player.dribbling.$invalid }"
              v-model.number="$v.player.dribbling.$model"
              required
            />
            <div v-if="$v.player.dribbling.$anyDirty && $v.player.dribbling.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.dribbling.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.dribbling.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.dribbling.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.dribbling.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.technique')" for="player-technique">Technique</label>
            <input
              type="number"
              class="form-control"
              name="technique"
              id="player-technique"
              data-cy="technique"
              :class="{ valid: !$v.player.technique.$invalid, invalid: $v.player.technique.$invalid }"
              v-model.number="$v.player.technique.$model"
              required
            />
            <div v-if="$v.player.technique.$anyDirty && $v.player.technique.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.technique.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.technique.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.technique.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.technique.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.confidence')" for="player-confidence">Confidence</label>
            <input
              type="number"
              class="form-control"
              name="confidence"
              id="player-confidence"
              data-cy="confidence"
              :class="{ valid: !$v.player.confidence.$invalid, invalid: $v.player.confidence.$invalid }"
              v-model.number="$v.player.confidence.$model"
              required
            />
            <div v-if="$v.player.confidence.$anyDirty && $v.player.confidence.$invalid">
              <small class="form-text text-danger" v-if="!$v.player.confidence.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.confidence.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.confidence.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.player.confidence.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.player.team')" for="player-team">Team</label>
            <select class="form-control" id="player-team" data-cy="team" name="team" v-model="player.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="player.team && teamOption.id === player.team.id ? player.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.player.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./player-update.component.ts"></script>
