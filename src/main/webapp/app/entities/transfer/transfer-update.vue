<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.transfer.home.createOrEditLabel"
          data-cy="TransferCreateUpdateHeading"
          v-text="$t('proFootballClubApp.transfer.home.createOrEditLabel')"
        >
          Create or edit a Transfer
        </h2>
        <div>
          <div class="form-group" v-if="transfer.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="transfer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.transfer.price')" for="transfer-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="transfer-price"
              data-cy="price"
              :class="{ valid: !$v.transfer.price.$invalid, invalid: $v.transfer.price.$invalid }"
              v-model.number="$v.transfer.price.$model"
              required
            />
            <div v-if="$v.transfer.price.$anyDirty && $v.transfer.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.transfer.price.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.transfer.price.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.transfer.password')" for="transfer-password">Password</label>
            <input
              type="text"
              class="form-control"
              name="password"
              id="transfer-password"
              data-cy="password"
              :class="{ valid: !$v.transfer.password.$invalid, invalid: $v.transfer.password.$invalid }"
              v-model="$v.transfer.password.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.transfer.bought')" for="transfer-bought">Bought</label>
            <input
              type="checkbox"
              class="form-check"
              name="bought"
              id="transfer-bought"
              data-cy="bought"
              :class="{ valid: !$v.transfer.bought.$invalid, invalid: $v.transfer.bought.$invalid }"
              v-model="$v.transfer.bought.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.transfer.player')" for="transfer-player">Player</label>
            <select class="form-control" id="transfer-player" data-cy="player" name="player" v-model="transfer.player">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="transfer.player && playerOption.id === transfer.player.id ? transfer.player : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
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
            :disabled="$v.transfer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./transfer-update.component.ts"></script>
